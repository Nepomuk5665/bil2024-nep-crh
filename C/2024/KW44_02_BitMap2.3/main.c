#include <stdio.h>
#include <stdint.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

// Struktur für RGB-Farben
typedef struct {
    unsigned char b;
    unsigned char g;
    unsigned char r;
    unsigned char padding;  // Füge Padding hinzu für korrekte Ausrichtung
} RGB;

// Struktur für benannte Farben
typedef struct {
    const char* name;  // Verwende const char* statt char*
    RGB color;
} ColorName;

#pragma pack(push, 1)
// BMP Header Strukturen
typedef struct {
    uint16_t type;
    uint32_t size;
    uint16_t reserved1;
    uint16_t reserved2;
    uint32_t offset;
} BMPFileHeader;

typedef struct {
    uint32_t size;
    int32_t  width;
    int32_t  height;
    uint16_t planes;
    uint16_t bits;
    uint32_t compression;
    uint32_t imagesize;
    int32_t  xresolution;
    int32_t  yresolution;
    uint32_t ncolours;
    uint32_t importantcolours;
} BMPImageHeader;
#pragma pack(pop)

// Vordefinierte Farben mit korrekter Ausrichtung
static const ColorName COLOR_LIST[] = {
    {"RED",    {{0, 0, 255, 0}}},
    {"GREEN",  {{0, 255, 0, 0}}},
    {"BLUE",   {{255, 0, 0, 0}}},
    {"YELLOW", {{0, 255, 255, 0}}},
    {"WHITE",  {{255, 255, 255, 0}}},
    {"BLACK",  {{0, 0, 0, 0}}},
    {NULL,     {{0, 0, 0, 0}}}
};

void print_help() {

    printf("Verfügbare Farben:\n");
    for (int i = 0; COLOR_LIST[i].name != NULL; i++) {
        printf("  - %s\n", COLOR_LIST[i].name);
    }
    printf("  - Hex-Farben (#RRGGBB, z.B. #FF0000)\n\n");
}

void read_line(char* buffer, int size) {
    fgets(buffer, size, stdin);
    size_t len = strlen(buffer);
    if (len > 0 && buffer[len-1] == '\n') {
        buffer[len-1] = '\0';
    }
}

RGB hex_to_rgb(const char* hex) {
    RGB color = {0, 0, 0, 0};  // Initialisiere auch das Padding
    if (hex[0] != '#' || strlen(hex) != 7) return color;

    char hex_clean[7];
    strncpy(hex_clean, hex + 1, 6);
    hex_clean[6] = '\0';

    unsigned int rgb_val;
    sscanf(hex_clean, "%x", &rgb_val);

    color.r = (rgb_val >> 16) & 0xFF;
    color.g = (rgb_val >> 8) & 0xFF;
    color.b = rgb_val & 0xFF;

    return color;
}

RGB get_color_by_name(const char* name) {
    for (int i = 0; COLOR_LIST[i].name != NULL; i++) {
        if (strcmp(name, COLOR_LIST[i].name) == 0) {
            return COLOR_LIST[i].color;
        }
    }
    return hex_to_rgb(name);
}

void get_interactive_input(int* width, int* height, char* filename, RGB** colors, int* color_count) {
    char buffer[256];

    do {
        printf("Höhe (in Pixeln): ");
        read_line(buffer, sizeof(buffer));
        *height = atoi(buffer);
    } while (*height <= 0);

    do {
        printf("Breite (in Pixeln): ");
        read_line(buffer, sizeof(buffer));
        *width = atoi(buffer);
    } while (*width <= 0);

    do {
        printf("Dateinamen (z.B. bild.bmp): ");
        read_line(filename, 256);
    } while (strlen(filename) == 0);

    do {
        printf("Wie viele Farbstreifen? ");
        read_line(buffer, sizeof(buffer));
        *color_count = atoi(buffer);
    } while (*color_count <= 0);

    *colors = (RGB*)malloc(*color_count * sizeof(RGB));
    if (!*colors) {
        printf("Fehler bei der Speicherallokierung!\n");
        exit(1);
    }

    printf("\nVerfügbare Farben:\n");
    for (int i = 0; COLOR_LIST[i].name != NULL; i++) {
        printf("  - %s\n", COLOR_LIST[i].name);
    }
    printf("  - Oder Hex-Farben (#RRGGBB)\n\n");

    for (int i = 0; i < *color_count; i++) {
        do {
            printf("Bitte geben Sie Farbe %d ein: ", i + 1);
            read_line(buffer, sizeof(buffer));
            (*colors)[i] = get_color_by_name(buffer);
        } while ((*colors)[i].r == 0 && (*colors)[i].g == 0 && (*colors)[i].b == 0 &&
                strcmp(buffer, "BLACK") != 0);
    }
}

int create_bmp(const char* filename, int width, int height, RGB* colors, int color_count) {
    int padding = (4 - (width * 3) % 4) % 4;
    int filesize = 54 + (3 * width + padding) * height;

    BMPFileHeader filehdr = {
        .type = 0x4D42,
        .size = filesize,
        .reserved1 = 0,
        .reserved2 = 0,
        .offset = 54
    };

    BMPImageHeader imghdr = {
        .size = 40,
        .width = width,
        .height = height,
        .planes = 1,
        .bits = 24,
        .compression = 0,
        .imagesize = (3 * width + padding) * height,
        .xresolution = 2835,
        .yresolution = 2835,
        .ncolours = 0,
        .importantcolours = 0
    };

    FILE* file = fopen(filename, "wb");
    if (!file) {
        printf("Fehler: Konnte Datei nicht erstellen: %s\n", filename);
        return 1;
    }

    fwrite(&filehdr, sizeof(filehdr), 1, file);
    fwrite(&imghdr, sizeof(imghdr), 1, file);

    unsigned char pad[3] = {0, 0, 0};
    int stripe_height = height / color_count;

    for (int y = height - 1; y >= 0; y--) {
        int color_index = (height - 1 - y) / stripe_height;
        if (color_index >= color_count) color_index = color_count - 1;

        for (int x = 0; x < width; x++) {
            // Schreibe nur die RGB-Werte, nicht das Padding
            fwrite(&colors[color_index], 3, 1, file);
        }
        if (padding > 0) {
            fwrite(pad, padding, 1, file);
        }
    }

    fclose(file);
    return 0;
}

int main(int argc, char* argv[]) {
    int width = 0, height = 0;
    char filename[256] = {0};
    RGB* colors = NULL;
    int color_count = 0;

    if (argc == 2 && (strcmp(argv[1], "--help") == 0 ||
                      strcmp(argv[1], "-help") == 0 ||
                      strcmp(argv[1], "--h") == 0)) {
        print_help();
        return 0;
    }

    if (argc < 2) {
        printf("\nBMP-Datei Generator\n");

        get_interactive_input(&width, &height, filename, &colors, &color_count);
    } else {
        for (int i = 1; i < argc; i++) {
            if (strcmp(argv[i], "-h") == 0 && i + 1 < argc) {
                height = atoi(argv[++i]);
            }
            else if (strcmp(argv[i], "-w") == 0 && i + 1 < argc) {
                width = atoi(argv[++i]);
            }
            else if (strcmp(argv[i], "-f") == 0 && i + 1 < argc) {
                strncpy(filename, argv[++i], sizeof(filename) - 1);
            }
            else if (i > 6) {
                if (color_count == 0) {
                    int temp_i = i;
                    while (temp_i < argc) {
                        color_count++;
                        temp_i++;
                    }
                    colors = (RGB*)malloc(color_count * sizeof(RGB));
                    if (!colors) {
                        printf("Fehler: Speicherallokierung fehlgeschlagen\n");
                        return 1;
                    }
                }
                colors[i - 7] = get_color_by_name(argv[i]);
            }
        }
    }

    if (width <= 0 || height <= 0 || strlen(filename) == 0 || color_count == 0) {
        printf("Fehler: Ungültige oder fehlende Parameter\n\n");
        print_help();
        free(colors);
        return 1;
    }

    int result = create_bmp(filename, width, height, colors, color_count);

    if (result == 0) {
        printf("\nBMP-Datei erfolgreich erstellt: %s\n", filename);
        printf("Größe: %dx%d Pixel\n", width, height);
        printf("Anzahl Farbstreifen: %d\n", color_count);
    }

    free(colors);
    return result;
}