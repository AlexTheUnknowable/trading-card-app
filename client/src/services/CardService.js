import api from "./api";

export default {
    list() {
        return api.get('/cards').then(response => response.data);
    },
    get(id) {
        return api.get(`/cards/${id}`).then(response => response.data);
    },
    add(card) {
        return api.post('/cards', card).then(response => response.data);
    },
    delete(id) {
        return api.delete(`/cards/${id}`).then(response => response.data);
    }
}