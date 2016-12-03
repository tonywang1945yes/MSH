package blservice.blfactoryservice;

import blservice.hotelblservice.HotelBLService;
import blservice.orderblservice.OrderBLService;
import blservice.promotionblservice.PromotionBLService;
import blservice.userblservice.UserBLService;

/**
 * Created by Sorumi on 16/12/3.
 */
public interface BLFactoryService {

    public HotelBLService getHotelBLService();

    public OrderBLService getOrderBLService();

    public PromotionBLService getPromotionBLService();

    public UserBLService getUserBLService();

}
