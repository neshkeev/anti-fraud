import grpc from 'k6/net/grpc';
import { check, sleep } from 'k6';

const client = new grpc.Client();
client.load(['definitions'], 'anti_fraud.proto');

export default () => {
  client.connect('workflow-grpc:7777', {
    plaintext: true
  });

  const data = { from: 'Bert', to: 'Alice', amount: 5555 };
  const response = client.invoke('com.github.neshkeev.antifraud.AntiFraud/Decide', data);

  check(response, {
    'status is OK': (r) => r && r.status === grpc.StatusOK,
  });

  client.close();
};

