// src/next-auth.d.ts
import NextAuth from 'next-auth';

declare module 'next-auth' {
  interface User {
    token?: string;
  }

  interface Session {
    accessToken?: string;
  }

  interface JWT {
    accessToken?: string;
  }
}
