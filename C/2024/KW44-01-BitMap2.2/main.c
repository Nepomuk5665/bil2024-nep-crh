#include <stdio.h>
#include <stdint.h>
#include <stdlib.h>

#pragma pack(push, 1)  // Verhindert Padding in Strukturen

// BMP File Header (14 bytes)
typedef struct {
    uint16_t type;          // Magic identifier: 0x4d42
    uint32_t size;          // File size in bytes
    uint16_t reserved1;     // Not used
    uint16_t reserved2;     // Not used
    uint32_t offset;        // Offset to image data in bytes
} BMPFileHeader;

// BMP Image Header (40 bytes)
typedef struct {
    uint32_t size;                // Header size in bytes
    int32_t  width;              // Width of image
    int32_t  height;             // Height of image
    uint16_t planes;             // Number of colour planes
    uint16_t bits;               // Bits per pixel
    uint32_t compression;        // Compression type
    uint32_t imagesize;          // Image size in bytes
    int32_t  xresolution;        // Pixels per meter
    int32_t  yresolution;        // Pixels per meter
    uint32_t ncolours;           // Number of colours
    uint32_t importantcolours;   // Important colours
} BMPImageHeader;

#pragma pack(pop)

int main() {
    const int WIDTH = 20;
    const int HEIGHT = 20;
    const char* filename = "../red.bmp";

    // Berechne Padding
    int padding = (4 - (WIDTH * 3) % 4) % 4;
    int filesize = 54 + (3 * WIDTH + padding) * HEIGHT;

    // Erstelle File Header
    BMPFileHeader filehdr = {
        .type = 0x4D42,          // BM
        .size = filesize,
        .reserved1 = 0,
        .reserved2 = 0,
        .offset = 54
    };

    // Erstelle Image Header
    BMPImageHeader imghdr = {
        .size = 40,
        .width = WIDTH,
        .height = HEIGHT,
        .planes = 1,
        .bits = 24,              // 24-bit Farbtiefe
        .compression = 0,        // Keine Kompression
        .imagesize = (3 * WIDTH + padding) * HEIGHT,
        .xresolution = 2835,     // 72 DPI
        .yresolution = 2835,     // 72 DPI
        .ncolours = 0,
        .importantcolours = 0
    };

    // Öffne Datei zum Schreiben
    FILE* file = fopen(filename, "wb");
    if (!file) {
        printf("Konnte Datei nicht erstellen\n");
        return 1;
    }

    // Schreibe Header
    fwrite(&filehdr, sizeof(filehdr), 1, file);
    fwrite(&imghdr, sizeof(imghdr), 1, file);

    // Schreibe Pixeldaten
    // BGR Format
    unsigned char pixel[3] = {0, 0, 255};  // Rot in BGR
    unsigned char pad[3] = {0, 0, 0};      // Padding bytes

    // Schreibe die Pixel zeilenweise
    for (int y = 0; y < HEIGHT; y++) {
        for (int x = 0; x < WIDTH; x++) {
            fwrite(pixel, 3, 1, file);
        }
        // Füge Padding am Ende jeder Zeile hinzu
        if (padding > 0) {
            fwrite(pad, padding, 1, file);
        }
    }

    fclose(file);
    printf("BMP-Datei '%s' wurde erfolgreich erstellt\n", filename);
    return 0;
}