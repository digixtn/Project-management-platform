// app/sign-in/page.tsx
import SignInLayer from '@/components/SignInLayer';

export const metadata = {
  title: "Sign in",
  description: "Wowdash NEXT JS is a developer-friendly, ready-to-use admin template.",
};

export default async function Page() {
  // Simulate data fetching on the server
  const data = await fetchSomeData();

  console.log('bruh');

  return (
    <>
      <SignInLayer />
      <div>{data}</div>
    </>
  );
}

async function fetchSomeData() {
  // Simulate fetching data from an API or database
  return "Fetched data from server";
}
