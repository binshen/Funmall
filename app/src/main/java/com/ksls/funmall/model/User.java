package com.ksls.funmall.model;

/**
 * Created by bin.shen on 2016/1/28.
 */
public class User {
    private Integer id;
    private String username;
    private String rel_name;
    private Integer admin_group;
    private Integer manager_group;
    private Integer company_id;
    private Integer subsidiary_id;
    private String tel;
    private String pic;
    private String ticket;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRel_name() {
        return rel_name;
    }

    public void setRel_name(String rel_name) {
        this.rel_name = rel_name;
    }

    public Integer getAdmin_group() {
        return admin_group;
    }

    public void setAdmin_group(Integer admin_group) {
        this.admin_group = admin_group;
    }

    public Integer getManager_group() {
        return manager_group;
    }

    public void setManager_group(Integer manager_group) {
        this.manager_group = manager_group;
    }

    public Integer getCompany_id() {
        return company_id;
    }

    public void setCompany_id(Integer company_id) {
        this.company_id = company_id;
    }

    public Integer getSubsidiary_id() {
        return subsidiary_id;
    }

    public void setSubsidiary_id(Integer subsidiary_id) {
        this.subsidiary_id = subsidiary_id;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}
