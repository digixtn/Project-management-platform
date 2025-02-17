// app/sign-in/page.tsx
import SignInLayer from '@/components/SignInLayer';

export const metadata = {
  title: "Sign in",
  description: "Wowdash NEXT JS is a developer-friendly, ready-to-use admin template.",
};

export default async function Page() {
  console.log('bruh');

  return (
    <>
      <SignInLayer />
    </>
  );
}

