package tripleS.backend.dto;

public class MentorRegisterDTO {
    // Infos du compte Utilisateur
    private String name;
    private String email;
    private String password; // Sera inséré dans la table users

    // Infos spécifiques du profil Mentor
    private String title;
    private String expertise;
    private String bio;
    private String linkedinUrl;
    private String imageUrl;

    // Getters et Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getExpertise() { return expertise; }
    public void setExpertise(String expertise) { this.expertise = expertise; }
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    public String getLinkedinUrl() { return linkedinUrl; }
    public void setLinkedinUrl(String linkedinUrl) { this.linkedinUrl = linkedinUrl; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}