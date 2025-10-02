export interface ItemVO {
    id: number
    userId: number
    isSeller: boolean
    stateCode: number

    name: string
    price: number
    description: string
    postage: string
    returnPeriod: string
    deliveryPeriod: string

    favorites: number

    createdAt: string
    updatedAt: string

    images: string[]
    tags: string[]
}

export interface ItemEditFormVO {
    stateCode: number

    name: string
    price: number
    description: string
    postage: string
    returnPeriod: string
    deliveryPeriod: string

    images: number[]
    tags: string[]
}