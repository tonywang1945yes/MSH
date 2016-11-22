package dataimpl.userdataimpl;

import datahelper.DataHelper;
import datahelper.HibernateHelper;
import dataservice.userdataservice.UserDataService;
import po.*;
import util.LoginState;
import util.ResultMessage;

import java.util.ArrayList;

/**
 * Created by Kray on 2016/11/17.
 */
public class UserDataServiceImpl implements UserDataService {

    private DataHelper<UserPO> userDataHelper=new HibernateHelper<UserPO>();
    private DataHelper<ClientPO> clientDataHelper = new HibernateHelper<ClientPO>();
    private DataHelper<StaffPO> staffDataHelper = new HibernateHelper<StaffPO>();
    private DataHelper<CreditPO> creditDataHelper = new HibernateHelper<CreditPO>();
    private DataHelper<SalesmanPO> salesmanDataHelper=new HibernateHelper<SalesmanPO>();

    protected UserDataServiceImpl(DataHelper<UserPO> userDataHelper) {
        //this.userDataHelper = userDataHelper;
    }

    /**
     * 登录
     *
     * @param account
     * @param password
     * @return
     */
    public LoginState login(String account, String password) {
        System.out.println(account);
        System.out.println(password);
        UserPO userPO;
        if ((userPO = userDataHelper.exactlyQuery("account", account)) != null) {
            UserPO tmpUserPO;
            if ((tmpUserPO = userDataHelper.exactlyQuery("password", password)) != null) {
                if (userPO.getPassword().equals(tmpUserPO.getPassword())) {
                    return LoginState.LOGIN_SUCCESS_Client;
                } else {
                    return LoginState.LOGIN_FAIL;
                }
            } else {
                return LoginState.LOGIN_FAIL;
            }
        } else if ((userPO = userDataHelper.exactlyQuery("account", account)) != null) {
            UserPO tmpUserPO;
            if ((tmpUserPO = userDataHelper.exactlyQuery("password", password)) != null) {
                if (userPO.getPassword().equals(tmpUserPO.getPassword())) {
                    return LoginState.LOGIN_SUCCESS_Staff;
                } else {
                    return LoginState.LOGIN_FAIL;
                }
            } else {
                return LoginState.LOGIN_FAIL;
            }
        } else if ((userPO = userDataHelper.exactlyQuery("account", account)) != null) {
            UserPO tmpUserPO;
            if ((tmpUserPO = userDataHelper.exactlyQuery("password", password)) != null) {
                if (userPO.getPassword().equals(tmpUserPO.getPassword())) {
                    return LoginState.LOGIN_SUCCESS_Salesman;
                } else {
                    return LoginState.LOGIN_FAIL;
                }
            } else {
                return LoginState.LOGIN_FAIL;
            }
        } else {
            return LoginState.LOGIN_FAIL;
        }
    }

    /**
     * 登出
     *
     * @return
     */
    public LoginState logout() {
        return LoginState.LOGOUT;
    }

    //TODO
    /**
     * 重置密码
     *
     * @param account
     * @param oldPassword
     * @param newPassword
     * @return
     */
    public ResultMessage resetPassword(String account, String oldPassword, String newPassword) {
        return null;
    }

    /**
     * 增加客户
     *
     * @param clientPO
     * @param creditPO
     * @return
     */
    public ResultMessage addClient(ClientPO clientPO, CreditPO creditPO) {
        if (creditDataHelper.save(clientPO) == ResultMessage.SUCCESS
                && creditDataHelper.save(creditPO) == ResultMessage.SUCCESS) {
            return ResultMessage.SUCCESS;
        } else {
            return ResultMessage.FAILED;
        }
    }

    /**
     * 根据ID寻找客户
     *
     * @param clientID
     * @return
     */
    public ClientPO searchClientByID(String clientID) {
        return clientDataHelper.exactlyQuery("clientID", clientID);
    }

    /**
     * 更新客户信息
     *
     * @param clientID
     * @param clientPO
     * @return
     */
    public ResultMessage updateClient(String clientID, ClientPO clientPO) {
        return userDataHelper.update(clientPO);
    }

    /**
     * 删除客户
     *
     * @param clientID
     * @return
     */
    public ResultMessage deleteClient(String clientID) {
        //也要删除信用记录
        if (deleteAllCredit(clientID)
                && clientDataHelper.delete("clientID", clientID) == ResultMessage.SUCCESS) {
            return ResultMessage.SUCCESS;
        } else {
            return ResultMessage.FAILED;
        }
    }

    /**
     * 搜索符合关键词的客户
     *
     * @param keyword
     * @return
     */
    public ArrayList<ClientPO> searchClient(String keyword) {
        ArrayList<ClientPO> clientPOs = new ArrayList<ClientPO>();
        for (ClientPO clientPO : clientDataHelper.fuzzyMatchQuery("clientID", keyword)) {
            if (!clientPOs.contains(clientPO)) {
                clientPOs.add(clientPO);
            }
        }
        for (ClientPO clientPO : clientDataHelper.fuzzyMatchQuery("clientName", keyword)) {
            if (!clientPOs.contains(clientPO)) {
                clientPOs.add(clientPO);
            }
        }
        return clientPOs;
    }

    /**
     * 增加酒店工作人员
     *
     * @param staffPO
     * @return
     */
    public ResultMessage addStaff(StaffPO staffPO) {
        return userDataHelper.save(staffPO);
    }

    /**
     * 根据ID查找酒店工作人员
     *
     * @param staffID
     * @return
     */
    public StaffPO searchStaffByID(String staffID) {
        return staffDataHelper.exactlyQuery("staffID", staffID);
    }

    /**
     * 更新酒店工作人员信息
     *
     * @param staffID
     * @param staffPO
     * @return
     */
    public ResultMessage updateStaff(String staffID, StaffPO staffPO) {
        return userDataHelper.update(staffPO);
    }

    /**
     * 删除酒店工作人员
     *
     * @param staffID
     * @return
     */
    public ResultMessage deleteStaff(String staffID) {
        return userDataHelper.delete("staffID", staffID);
    }

    /**
     * 搜索符合关键词的酒店工作人员
     *
     * @param keyword
     * @return
     */
    public ArrayList<StaffPO> searchStaff(String keyword) {
        ArrayList<StaffPO> staffPOs = new ArrayList<StaffPO>();
        for (StaffPO staffPO : staffDataHelper.fuzzyMatchQuery("staffID", keyword)) {
            if (!staffPOs.contains(staffPO)) {
                staffPOs.add(staffPO);
            }
        }
        for (StaffPO staffPO : staffDataHelper.fuzzyMatchQuery("staffName", keyword)) {
            if (!staffPOs.contains(staffPO)) {
                staffPOs.add(staffPO);
            }
        }
        for (StaffPO staffPO : staffDataHelper.fuzzyMatchQuery("hotelID", keyword)) {
            if (!staffPOs.contains(staffPO)) {
                staffPOs.add(staffPO);
            }
        }
        return staffPOs;
    }

    /**
     * 增加网站营销人员
     *
     * @param salesmanPO
     * @return
     */
    public ResultMessage addSalesman(SalesmanPO salesmanPO) {
        return userDataHelper.save(salesmanPO);
    }

    /**
     * 根据ID查找网站营销人员
     *
     * @param salesmanID
     * @return
     */
    public SalesmanPO searchSalesmanByID(String salesmanID) {
        return salesmanDataHelper.exactlyQuery("salesmanID", salesmanID);
    }

    /**
     * 更新网站营销人员信息
     *
     * @param salesmanID
     * @param salesmanPO
     * @return
     */
    public ResultMessage updateSalesman(String salesmanID, SalesmanPO salesmanPO) {
        return userDataHelper.update(salesmanPO);
    }

    /**
     * 删除网站营销人员
     *
     * @param salesmanID
     * @return
     */
    public ResultMessage deleteSalesman(String salesmanID) {
        return userDataHelper.delete("salesmanID", salesmanID);
    }

    /**
     * 搜索符合关键词的网站营销人员
     *
     * @param keyword
     * @return
     */
    public ArrayList<SalesmanPO> searchSalesman(String keyword) {
        ArrayList<SalesmanPO> salesmanPOs = new ArrayList<SalesmanPO>();
        for (SalesmanPO salesmanPO : salesmanDataHelper.fuzzyMatchQuery("salesmanID", keyword)) {
            if (!salesmanPOs.contains(salesmanPO)) {
                salesmanPOs.add(salesmanPO);
            }
        }
        for (SalesmanPO salesmanPO : salesmanDataHelper.fuzzyMatchQuery("salesmanName", keyword)) {
            if (!salesmanPOs.contains(salesmanPO)) {
                salesmanPOs.add(salesmanPO);
            }
        }
        return salesmanPOs;
    }

    /**
     * 给客户增加一条信用记录
     *
     * @param clientID
     * @param creditPO
     * @return
     */
    public ResultMessage addCreditRecord(String clientID, CreditPO creditPO) {
        //这个 clientID 好像没用?
        return userDataHelper.save(creditPO);
    }

    /**
     * 根据客户ID查找所有的信用记录
     *
     * @param clientID
     * @return
     */
    public ArrayList<CreditPO> searchCreditByID(String clientID) {
        ArrayList<CreditPO> creditPOs = new ArrayList<CreditPO>();
        for (CreditPO creditPO : creditDataHelper.prefixMatchQuery("clientID", clientID)) {
            if (!creditPOs.contains(creditPO)) {
                creditPOs.add(creditPO);
            }
        }
        return creditPOs;
    }

    /**
     * 删除客户对应所有信用记录
     *
     * @param clientID
     * @return 是否删除成功
     */
    private boolean deleteAllCredit(String clientID) {
        ArrayList<CreditPO> creditPOs = searchCreditByID(clientID);
        for (CreditPO creditPO : creditPOs) {
            if(creditDataHelper.delete("orderID", creditPO.getOrderID()) == ResultMessage.FAILED){
                return false;
            };
        }
        return true;
    }
}