import { Router, Request, Response } from 'express';
import axios from 'axios';

const router: Router = Router();
const gatewayUrl = 'http://localhost:3000';

router.post('/receive-payment/:userId', async (req: Request, res: Response) => {
  const { userId } = req.params;
  const { amount } = req.body;

  try {
    const response = await axios.post(`${gatewayUrl}/receive-payment/${userId}`, { amount });
    res.json(response.data);
  } catch (error) {
    res.status(500).json({ error: 'Erro ao receber pagamento' });
  }
});

router.post('/make-payment/:userId', async (req: Request, res: Response) => {
  const { userId } = req.params;
  const { amount } = req.body;

  try {
    const response = await axios.post(`${gatewayUrl}/make-payment/${userId}`, { amount });
    res.json(response.data);
  } catch (error) {
    res.status(500).json({ error: 'Erro ao realizar pagamento' });
  }
});

export { router };
