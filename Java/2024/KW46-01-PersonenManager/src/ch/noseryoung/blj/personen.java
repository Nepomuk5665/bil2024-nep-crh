package ch.noseryoung.blj;

public class personen {
    private String name;
    private String lieblingsfarbe;
    private String Eigenschaft;
    public String getName() {
        return name;
    }

    public String getEigenschaft() {
        return Eigenschaft;
    }

    public void setEigenschaft(String eigenschaft, int access) {

        if (access == 1){
            this.Eigenschaft = eigenschaft;
        }else if (access == 0){
            System.out.println("du dörsch das nöd mache");
        }


    }

    public void setName(String name, int access) {

        if (access == 1){
            this.name = name;
        }else if (access == 0){
            System.out.println("du dörsch das nöd mache");
        }

    }

    public String getLieblingsfarbe() {
        return lieblingsfarbe;
    }

    public void setLieblingsfarbe(String lieblingsfarbe, int access) {
        if (access == 1){
            this.lieblingsfarbe = lieblingsfarbe;
        }else if (access == 0){
            System.out.println("du dörsch das nöd mache");
        }


    }
}
