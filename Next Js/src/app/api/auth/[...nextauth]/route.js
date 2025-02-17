// src/app/api/auth/[...nextauth]/route.js
import NextAuth from 'next-auth';
import CredentialsProvider from 'next-auth/providers/credentials';

export const authOptions = {
  providers: [
    CredentialsProvider({
      name: 'Credentials', // Important: Give a name if you haven't already
      credentials: {
        email: { label: 'Email', type: 'text' },
        password: { label: 'Password', type: 'password' },
      },
      async authorize(credentials) {
        const { email, password } = credentials;

        try {
          const response = await fetch('http://localhost:8080/api/v1/auth/authenticate', { // Your Spring Boot endpoint
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
            },
            body: JSON.stringify({ email, password }),
          });

          if (!response.ok) {
            const errorData = await response.json(); // Try to get error details from the backend
            const errorMessage = errorData?.message || response.statusText; // Fallback to status text
            throw new Error(errorMessage); // Throw an error to be caught by NextAuth.js
          }

          const userData = await response.json(); // Data from your backend

          // Important: Adapt this to match your backend's response structure
          // It MUST return an object with a `user` property
          return {
            user: {
              id: userData.id, // Or whatever user identifier your backend provides
              name: userData.name, // If you have a name field
              email: userData.email,
              // ... other user details
            },
            token: userData.token, // JWT token from the backend
          };

        } catch (error) {
          console.error("Authentication error:", error);
          throw new Error(error.message); // Re-throw the error for NextAuth.js
        }
      },
    }),
  ],
  secret: process.env.NEXTAUTH_SECRET, // VERY IMPORTANT: Set a strong secret in production!
  session: {
    strategy: 'jwt',
  },
  callbacks: {
    async jwt({ token, user, account }) {
      if (account && user) {
        token.accessToken = token.token || user.token;
        token.user = user;
      }
      return token;
    },
    async session({ session, token }) {
      session.accessToken = token.accessToken;
      session.user = token.user;
      return session;
    },
  },
};

const handler = NextAuth(authOptions);
export { handler as GET, handler as POST };