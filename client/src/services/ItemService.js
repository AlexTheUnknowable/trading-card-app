import api from "./api";

export default {
    list() {
        return api.get('/items').then(response => response.data);
    },
    get(id) {
        return api.get(`/items/${id}`).then(response => response.data);
    },
    getMyCards() {
        return api.get('/mycards').then(response => response.data);
    },
    getMyUniqueCards() {
        return api.get('/mycards/unique').then(response => response.data);
    },
    listStore() {
        return api.get('/store').then(response => response.data);
    },
    add(item) {
        return api.post('/items', item).then(response => response.data);
    },
    update(id, item) {
        return api.put(`/items/${id}`, item).then(response => response.data);
    },
    delete(id) {
        return api.delete(`/items/${id}`).then(() => {});
    }
}