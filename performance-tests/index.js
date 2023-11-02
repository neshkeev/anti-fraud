import http from 'k6/http';

export default function () {
  const url = 'http://workflow:18080/check';
  const payload = JSON.stringify({ from: 'sHbM54E6ULirHw35DZBCLj79All9eUrc', to: 'hello' });

  const params = {
    headers: {
      'Content-Type': 'application/json',
    },
  };

  http.post(url, payload, params);
}
