import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './UserBalance.css';

const UserBalance: React.FC = () => {
  const [userId, setUserId] = useState<number | null>(null);
  const [userName, setUserName] = useState('');
  const [balance, setBalance] = useState(0);
  const [receivedAmount, setReceivedAmount] = useState(0);
  const [paidAmount, setPaidAmount] = useState(0);
  const [newUserName, setNewUserName] = useState('');
  const [initialBalance, setInitialBalance] = useState(0);
  const [paymentHistory, setPaymentHistory] = useState([]);
  const [paymentId, setPaymentId] = useState<number | null>(null); // Estado para atualizar o histórico de pagamentos

  useEffect(() => {
    if (userId) {
      axios.get(`http://localhost:8080/users/${userId}`)
        .then((response) => {
          setUserName(response.data.name);
          setBalance(response.data.balance);
        })
        .catch((error) => {
          console.error('Erro ao obter os dados do usuário:', error);
        });
    }
  }, [userId]);

  useEffect(() => {
    if (balance) {
      axios.get(`http://localhost:8080/users/${userId}/payment-history`)
        .then((response) => {
          setPaymentHistory(response.data);
          console.log(response.data);
        })
        .catch((error) => {
          console.error('Erro ao obter o histórico de pagamentos:', error);
        });
    }
  }, [balance]);

  const handleReceivePayment = () => {
    if (userId) {
      axios.post(`http://localhost:8080/users/${userId}/receive-payment`, {
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
      axios.post(`http://localhost:8080/users/${userId}/make-payment`, {
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
    const userDetails = {
      name: newUserName,
      balance: initialBalance,
    };
    
    axios.post('http://localhost:8080/users/create-user', userDetails)
      .then((response) => {
        setUserId(response.data);
        setUserName(newUserName);
        setBalance(initialBalance);
      })
      .catch((error) => {
        console.error('Erro ao criar usuário:', error);
      });
  };

  return (
    <div id="modalContainer">
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
          <h2>Histórico de Pagamentos</h2>
          <ul>
            {paymentHistory.map((payment: any) => (
              <li key={payment.id}>
                {payment.isSum ? '+' : '-' }{payment.amount} em {payment.timestamp}
              </li>
            ))}
          </ul>
        </>
      ) : (
        <div>
          <h2>Criar Usuário</h2>
          <input
            type="text"
            value={newUserName}
            onChange={(e) => setNewUserName(e.target.value)}
            placeholder="Nome do usuário"
          />
          <input
            type="number"
            value={initialBalance}
            onChange={(e) => setInitialBalance(+e.target.value)}
            placeholder="Balanço inicial"
          />
          <button onClick={createUser}>Criar Usuário</button>
        </div>
      )}
    </div>
  );
};

export default UserBalance;
