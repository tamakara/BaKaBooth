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

export interface ItemCardVO {
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
}

export interface ItemEditFormVO {
    stateCode: number
    name: string
    description: string
    images: number[]
    tags: string[]
    variations: VariationsEditFormVO[]
    deliveryTime: string
    shippingDetails: string
    returnPolicy: string
    warrantyPeriod: string
}

export interface ItemVO {
    userId: number
    name: string
    description: string
    favorites: number
    imageUrls: string[]
    tags: string[]
    variations: VariationVO[]
    deliveryTime: string
    shippingDetails: string
    returnPolicy: string
    warrantyPeriod: string
}

export interface VariationVO {
    id: number
    name: string
    type: string
    price: number
    stock: number
}

