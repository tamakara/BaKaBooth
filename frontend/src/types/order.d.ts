export interface OrderVO {
    id: number
    userId: number
    itemId: number
    stateCode: number
    // 0 - 已取消, 1 - 待付款, 2 - 待发货, 3 - 待收货, 4 - 已完成, 5 - 退货中, 6 - 退款中

    payAmount: number

    createdAt: string
    payAt: string
    deliveryAt: string
    receiveAt: string
    finishAt: string
    returnRequestAt: string
    returnAt: string
    refundAt: string
    returnFinishAt: string
}