import api from './api';

export interface Card {
  id: number;
  name: string;
  rarity: number;
}

export default {
    list(): Promise<Card[]> {
        return api.get<Card[]>('/cards').then(response => response.data);
    },
    get(id: string): Promise<Card> {
        return api.get<Card>(`/cards/${id}`).then(response => response.data);
    },
    add(card: Omit<Card, "id">): Promise<Card> {
        return api.post<Card>('/cards', card).then(response => response.data);
    },
    update(id: string, card: Partial<Card>): Promise<Card> {
        return api.put<Card>(`/cards/${id}`, card).then(response => response.data);
    },
    delete(id: string): Promise<void> {
        return api.delete(`/cards/${id}`).then(() => {});
    }
}
