export interface ItemManageVO {
    id: number
    name: string
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

export interface VariationsEditFormVO {
    name: string
    price: number
    stock: number
    files: string[]
}

export interface ItemEditFormVO {
    state: string
    name: string
    description: string
    images: string[]
    tags: string[]
    variations: VariationsEditFormVO[]

}