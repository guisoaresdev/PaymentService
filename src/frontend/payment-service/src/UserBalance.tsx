import React, { useState, useEffect } from 'react';
import axios from 'axios';

const UserBalance: React.FC = () => {
  const [userId, setUserId] = useState<number | null>(null);
  const [userName, setUserName] = useState('');
  const [balance, setBalance] = useState(0);
  const [receivedAmount, setReceivedAmount] = useState(0);
  const [paidAmount, setPaidAmount] = useState(0);

  useEffect(() => {
    if (userId) {
      axios.get(`http://localhost:3000/user/${userId}`)
        .then((response) => {
          setUserName(response.data.name);
          setBalance(response.data.balance);
        })
        .catch((error) => {
          console.error('Erro ao obter os dados do usuário:', error);
        });
    }
  }, [userId]);

  const handleReceivePayment = () => {
    if (userId) {
      axios.post(`http://localhost:3000/receive-payment/${userId}`, {
        amount: receivedAmount,
      })
      .then((response) => {
        console.log('Pagamento recebido com sucesso:', response.data);
        setBalance(balance + receivedAmount);
        setReceivedAmount(0);
      })
      .catch((error) => {
        console.error('Erro ao receber pagamento:', error);
      });
    }
  };

  const handleMakePayment = () => {
    if (userId) {
      axios.post(`http://localhost:3000/make-payment/${userId}`, {
        amount: paidAmount,
      })
      .then((response) => {
        console.log('Pagamento realizado com sucesso:', response.data);
        setBalance(balance - paidAmount);
        setPaidAmount(0);
      })
      .catch((error) => {
        console.error('Erro ao realizar pagamento:', error);
      });
    }
  };

  const createUser = () => {
    axios.post('http://localhost:3000/create-user')
      .then((response) => {
        setUserId(response.data.userId);
      })
      .catch((error) => {
        console.error('Erro ao criar usuário:', error);
      });
  };

  return (
    <div>
      {userId ? (
        <>
          <h2>Detalhes da conta</h2>
          <p>Nome do usuário: {userName}</p>
          <p>Saldo: {balance}</p>
          <div>
            <input
              type="number"
              value={receivedAmount}
              onChange={(e) => setReceivedAmount(+e.target.value)}
            />
            <button onClick={handleReceivePayment}>Receber Pagamento</button>
          </div>
          <div>
            <input
              type="number"
              value={paidAmount}
              onChange={(e) => setPaidAmount(+e.target.value)}
            />
            <button onClick={handleMakePayment}>Fazer Pagamento</button>
          </div>
        </>
      ) : (
        <button onClick={createUser}>Criar Usuário</button>
      )}
    </div>
  );
};

export default UserBalance;
