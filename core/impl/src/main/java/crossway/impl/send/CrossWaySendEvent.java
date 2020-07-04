package crossway.impl.send;

/**
 * description:
 *
 * @author: Johnson Wang
 * @date: 2020/7/3 17:03
 * @copyright: 2020, FA Software (Shanghai) Co., Ltd. All Rights Reserved.
 */
public interface CrossWaySendEvent {

    Object event(Object message);
}