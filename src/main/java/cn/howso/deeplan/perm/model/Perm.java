package cn.howso.deeplan.perm.model;

public class Perm {

    private Integer id;

    private String note;

    private String pattern;

    private Integer spaceId;
    private Integer uriId;

    public Integer getUriId() {
        return uriId;
    }

    public void setUriId(Integer uriId) {
        this.uriId = uriId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern == null ? null : pattern.trim();
    }

    public Integer getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(Integer spaceId) {
        this.spaceId = spaceId;
    }
}
