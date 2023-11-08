package com.codegama.Taskscheduler.activity;

public class model_class {

    // model class for data of current update

    private Integer total;
    private  Integer quit;
    private String username;
    private String img_url;


    public model_class(Integer total, Integer quit, String username, String img_url) {
        this.total = total;
        this.quit = quit;
        this.username = username;
        this.img_url = img_url;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getQuit() {
        return quit;
    }

    public void setQuit(Integer quit) {
        this.quit = quit;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public model_class()
    {

    }

}
