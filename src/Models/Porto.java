package Models;

public class Porto {
    private String Id;
    private String Nome;

    public Porto(String id, String nome) {
        Id = id;
        Nome = nome;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }
}
