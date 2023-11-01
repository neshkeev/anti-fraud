import http from 'k6/http';

export default function () {
  const url = 'http://workflow:18080/check';
  const payload = JSON.stringify({});

  const params = {
    headers: {
      'Content-Type': 'application/json',
    },
  };

  http.post(url, payload, params);
}
