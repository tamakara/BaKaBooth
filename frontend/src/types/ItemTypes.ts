export interface ItemManageDTO {
    itemId: number
    itemName: string
    state: string,
    favorites: number
    itemUrl: string
    coverUrl: string

    variations: Array<{
        name: string,
        price: string
        stock: number
        sales: number
        proceeds: number
    }>
}

export interface ItemDisplayDTO {
    itemId: string
    itemName: string
    shopName: string
    favorites: number
    price: string
    itemUrl: string
    coverUrl: string
}