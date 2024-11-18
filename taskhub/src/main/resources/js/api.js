export function sendGET(url) {
    return fetch(url, { headers: { 'Accept': 'application/json' } })
        .then((response) => (response.ok ? response.json() : []));
}

export function sendPOST(url, data) {
    return fetch(url, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data),
    });
}

export function sendDELETE(url) {
    return fetch(url, { method: 'DELETE' });
}
