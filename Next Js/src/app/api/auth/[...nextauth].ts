// src/app/api/auth/[...nextauth].ts
import NextAuth from 'next-auth';
import CredentialsProvider from 'next-auth/providers/credentials';
import { authenticateUser } from '../../../lib/auth/api';

export default NextAuth({
  providers: [
    CredentialsProvider({
      name: 'Credentials',
      credentials: {
        email: { label: 'Email', type: 'email' },
        password: { label: 'Password', type: 'password' },
      },
      authorize: async (credentials) => {
        try {
          const user = await authenticateUser(credentials.email, credentials.password);
          return user;
        } catch (error) {
          console.error('Authentication error:', error);
          return null;
        }
      },
    }),
  ],
  callbacks: {
    async jwt({ token, user }) {
      if (user) {
        // Ensure token.accessToken is treated as a string
        token.accessToken = user.token as string;
      }
      return token;
    },
    async session({ session, token }) {
      // Ensure session.accessToken is treated as a string
      session.accessToken = token.accessToken as string;
      return session;
    },
  },
  pages: {
    signIn: '/auth/sign-in',
  },
  secret: process.env.NEXTAUTH_SECRET,
});
