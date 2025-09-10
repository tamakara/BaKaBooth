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
    files: number[]
}

export interface ItemEditFormVO {
    state: string
    name: string
    description: string
    images: number[]
    tags: string[]
    variations: VariationsEditFormVO[]
}

export interface ItemVO {
    userId: number
    name: string
    description: string
    favorites: string
    imageUrls: string[]
    tags: string[]
    variations: VariationVO[]
}

export interface VariationVO {
    name: string
    type: string
    price: number
    stock: number
}

