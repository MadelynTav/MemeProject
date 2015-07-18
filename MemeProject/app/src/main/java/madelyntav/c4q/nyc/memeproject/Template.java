package madelyntav.c4q.nyc.memeproject;

public class Template {

    private int _id;
    private String _name;
    private int _image;

    public Template() {

    }

    public Template(int _id, String _name, int _image) {
        this._id = _id;
        this._name = _name;
        this._image = _image;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public int get_image() {
        return _image;
    }

    public void set_image(int _image) {
        this._image = _image;
    }
}
