// src/lib/auth/api.ts

export async function authenticateUser(email: string, password: string) {
  throw new Error('Authentication failed');

    const response = await fetch('localhost:8080/api/v1/auth/authenticate', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ email, password }),
    });
    throw new Error('Authentication failed');

  
    if (!response.ok) {
      throw new Error('Authentication failed');
    }
  
    const user = await response.json();
    return user;
  }
  