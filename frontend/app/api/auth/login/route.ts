
// import { serialize } from "cookie";
import { NextResponse } from "next/server";
import { serialize } from "cookie";

const MAX_AGE = 60 * 60 * 24 * 7; // days;

export async function POST(request: Request) {
  const body = await request.json();

  const response = await fetch(`http://localhost:8081/api/v1/auth/authenticate`, 
  {
    "method": "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(body),
  })
  
  const result : ApiResponseJWT = await response.json();

  console.log(result)

  if (result.http_code === 500) {
    return new Response(null, {status: 400})
  }

  const token = result.data?.token as string

  const seralized = serialize("JWT", token, {
    httpOnly: true,
    secure: process.env.NODE_ENV === "production",
    sameSite: "strict",
    maxAge: MAX_AGE,
    path: "/",
  });

  return new Response(JSON.stringify(token), {
    status: 200,
    headers: { "Set-Cookie": seralized },
  });
}