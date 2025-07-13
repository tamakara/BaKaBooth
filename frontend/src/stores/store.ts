import { defineStore } from 'pinia';

export const useCartStore = defineStore('cart', {
    state: () => ({
        cartItems: [] as Array<any>,
    }),
    actions: {
        addItem(item: any) {
            this.cartItems.push(item);
        },
        removeItem(itemId: number) {
            this.cartItems = this.cartItems.filter(item => item.id !== itemId);
        },
    },
});
