const BASE_URL = 'http://localhost:8080';

export const api = {
  
  register: async (name: string, email: string, password: string) => {
    const response = await fetch(`${BASE_URL}/users/register`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ name, email, password }),
    });

    if (!response.ok) {
      const errorData = await response.json();
      throw new Error(errorData.erro || 'Erro ao realizar o cadastro. Tente novamente.');
    }

    return response.json();
  },

  login: async (email: string, password: string) => {
    const response = await fetch(`${BASE_URL}/users/login`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ email, password }),
    });

    if (!response.ok) {
      const errorData = await response.json();
      throw new Error(errorData.erro || 'E-mail ou senha inválidos.');
    }

    return response.json();
  },
  
  getGames: async () => {
    const response = await fetch(`${BASE_URL}/games/all`);
    if (!response.ok) {
      throw new Error('Erro ao buscar o catálogo de jogos.');
    }
    return response.json();
  },

  saveReview: async (userId: number, gameId: number, rating: number, comment: string) => {
    const response = await fetch(`${BASE_URL}/reviews/register`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ userId, gameId, rating, comment }),
    });
    
    if (!response.ok) {
      throw new Error('Erro ao salvar sua avaliação.');
    }

    const text = await response.text();
    return text ? JSON.parse(text) : { sucesso: true}
  },

  getReviewsByUser: async (userId: number) => {
    const response = await fetch(`${BASE_URL}/reviews/user/${userId}`);
    if (!response.ok) throw new Error('Erro ao buscar suas avaliações.');
    return response.json();
  },

};