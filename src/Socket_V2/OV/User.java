package Socket_V2.OV;

/**
 * @program: httpSocket
 * @description: 用户名
 * @author: xw
 * @create: 2018-09-16 14:03
 */
public class User {

    private String userId;
    private String userName;
    private String duty;
    private String department;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
