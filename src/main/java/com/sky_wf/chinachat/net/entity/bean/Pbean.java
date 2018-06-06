package com.sky_wf.chinachat.net.entity.bean;

/**
 * @Date :  2018/6/1
 * @Author : WF
 * @Description :
 */
public class Pbean {

    /**
     * id : 2752
     * description : 你的小可爱突然出现
     * path :
     * size : 0
     * width : 186
     * height : 179
     * created_at : 2016-12-09 10:32:47
     * updated_at : 2016-12-09 10:38:41
     * user_id : 1
     * permitted_at : 2016-12-09 10:38:41
     * disk :
     * hotpoint : 125
     * channel : wechat
     * upload_id : 9784
     * content :
     * image_url : https://www.zhuangbi.info/uploads/i/2016-12-09-b2b78f2272e9e70dacabb1cd5492e4fb.png
     * file_size : 4.68 KB
     * upload : {"id":9784,"name":null,"description":"你的小可爱突然出现","disk":"localuploads","path":"i/2016-12-09-b2b78f2272e9e70dacabb1cd5492e4fb.png","size":4797,"user_id":1,"created_at":"2016-12-09 10:32:47","updated_at":"2017-02-19 17:14:28","uploadable_id":null,"uploadable_type":null,"url":"https://www.zhuangbi.info/uploads/i/2016-12-09-b2b78f2272e9e70dacabb1cd5492e4fb.png"}
     */

    private int id;
    private String description;
    private String path;
    private int size;
    private int width;
    private int height;
    private String created_at;
    private String updated_at;
    private int user_id;
    private String permitted_at;
    private String disk;
    private int hotpoint;
    private String channel;
    private int upload_id;
    private String content;
    private String image_url;
    private String file_size;
    private UploadBean upload;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getPermitted_at() {
        return permitted_at;
    }

    public void setPermitted_at(String permitted_at) {
        this.permitted_at = permitted_at;
    }

    public String getDisk() {
        return disk;
    }

    public void setDisk(String disk) {
        this.disk = disk;
    }

    public int getHotpoint() {
        return hotpoint;
    }

    public void setHotpoint(int hotpoint) {
        this.hotpoint = hotpoint;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public int getUpload_id() {
        return upload_id;
    }

    public void setUpload_id(int upload_id) {
        this.upload_id = upload_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getFile_size() {
        return file_size;
    }

    public void setFile_size(String file_size) {
        this.file_size = file_size;
    }

    public UploadBean getUpload() {
        return upload;
    }

    public void setUpload(UploadBean upload) {
        this.upload = upload;
    }

    public static class UploadBean {
        /**
         * id : 9784
         * name : null
         * description : 你的小可爱突然出现
         * disk : localuploads
         * path : i/2016-12-09-b2b78f2272e9e70dacabb1cd5492e4fb.png
         * size : 4797
         * user_id : 1
         * created_at : 2016-12-09 10:32:47
         * updated_at : 2017-02-19 17:14:28
         * uploadable_id : null
         * uploadable_type : null
         * url : https://www.zhuangbi.info/uploads/i/2016-12-09-b2b78f2272e9e70dacabb1cd5492e4fb.png
         */

        private int id;
        private Object name;
        private String description;
        private String disk;
        private String path;
        private int size;
        private int user_id;
        private String created_at;
        private String updated_at;
        private Object uploadable_id;
        private Object uploadable_type;
        private String url;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getName() {
            return name;
        }

        public void setName(Object name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDisk() {
            return disk;
        }

        public void setDisk(String disk) {
            this.disk = disk;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public Object getUploadable_id() {
            return uploadable_id;
        }

        public void setUploadable_id(Object uploadable_id) {
            this.uploadable_id = uploadable_id;
        }

        public Object getUploadable_type() {
            return uploadable_type;
        }

        public void setUploadable_type(Object uploadable_type) {
            this.uploadable_type = uploadable_type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
