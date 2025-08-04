export interface ItemManageVO {
    itemId: number
    itemName: string
    state: string,
    favorite: number
    coverUrl: string

    variations: Array<{
        name: string,
        price: string
        stock: number
        sales: number
    }>
}

export interface ItemDisplayVO {
    itemId: string
    itemName: string
    shopId: number
    shopName: string
    favorite: number
    minPrice: number
    maxPrice: number
    coverUrl: string
}