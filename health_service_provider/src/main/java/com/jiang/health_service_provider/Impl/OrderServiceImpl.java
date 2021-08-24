package com.jiang.health_service_provider.Impl;

import com.jiang.constant.MessageConstant;
import com.jiang.entity.Result;
import com.jiang.health_service_provider.dao.MemberDao;
import com.jiang.health_service_provider.dao.OrderDao;
import com.jiang.health_service_provider.dao.OrderSettingDao;
import com.jiang.pojo.Member;
import com.jiang.pojo.Order;
import com.jiang.pojo.OrderSetting;
import com.jiang.service.OrderService;
import com.jiang.utils.DateUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private MemberDao memberDao;

    /**
     * 体检预约方法处理逻辑比较复杂，需要进行如下业务处理：
     * 1、检查用户所选择的预约日期是否已经提前进行了预约设置，如果没有设置则无法进行预约
     * 2、检查用户所选择的预约日期是否已经约满，如果已经约满则无法预约
     * 3、检查用户是否重复预约（同一个用户在同一天预约了同一个套餐），如果是重复预约则无法完成再次预约
     * 4、检查当前用户是否为会员，如果是会员则直接完成预约，如果不是会员则自动完成注册并进行预约
     * 5、预约成功，更新当日的已预约人数
     *
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public Result order(Map map) throws Exception {
        //检查当前日期是否进行了预约设置
        String orderDate = (String)map.get("orderDate");
        Date date = DateUtils.parseString2Date(orderDate);
        OrderSetting orderSetting = orderSettingDao.findByOrderDate(orderDate);
        if(orderSetting == null){
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        System.out.println(orderSetting.toString());
        //检查预约日期是否已满
        //可预约人数
        int number = orderSetting.getNumber();
        int reservations = orderSetting.getReservations();
        if(reservations >= number){
            return new Result(false,MessageConstant.ORDERSETTING_FAIL);
        }
        System.out.println("可预约人数未满");
        //检查当前用户是否是会员
        String telephone = (String) map.get("telephone");
        Member member = memberDao.findByTelephone(telephone);
//        System.out.println("member" + member.getId());
        //防止重复预约
        if(member != null){
            Integer memberId = member.getId();
            int setmealId = Integer.parseInt((String)map.get("setmealId"));
            Order order = new Order(memberId,orderDate,null,null,setmealId);
            List<Order> list = orderDao.findByCondition(order);
            if(list != null && list.size() > 0){
                //已经完成预约  不能重复预约
                return new Result(false,MessageConstant.ORDERSETTING_FAIL);
            }
        }
        System.out.println("防止重复预约");

        //当前用户不是会员，需要自动完成注册
        if(member == null){
            member = new Member();
            member.setName((String) map.get("name"));
            member.setPhoneNumber(telephone);
            member.setIdCard((String) map.get("idCard"));
            member.setSex((String) map.get("sex"));
            member.setRegTime(new Date());
            memberDao.add(member);
        }
        System.out.println("date" + date);
        Order order = new Order(member.getId(),
                orderDate,
                (String)map.get("orderType"),
                Order.ORDERSTATUS_NO,
                Integer.parseInt((String) map.get("setmealId")));
        System.out.println(order.getId());
        orderDao.add(order);
        System.out.println("order添加成功");
        //可以预约，设置预约人数加一
        orderSetting.setReservations(orderSetting.getReservations() + 1);
        orderSettingDao.editReservationsByOrderDate(orderSetting);
        System.out.println(orderSetting.toString());
        System.out.println("可以预约，设置预约人数加一");

        Integer orderId = orderDao.findByMember(order).getId();
        System.out.println(orderId);
        return new Result(true,MessageConstant.ORDER_SUCCESS,orderId);
    }


    //根据id查询预约详细信息（包括会员姓名、套餐名称、预约基本信息）
    public Map findById(Integer id) {
        Map map = orderDao.findById4Detail(id);
        if(map != null){
            //处理日期格式
            Date orderDate = (Date) map.get("orderDate");
            try {
                map.put("orderDate",DateUtils.parseDate2String(orderDate));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}
