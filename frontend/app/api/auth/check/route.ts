import { cookies } from "next/headers";
import { NextResponse } from "next/server";

export async function GET() {
  const cookieStore = cookies();

  const token = cookieStore.get('JWT');

  if (!token) {
    return NextResponse.json(
      {
        message: "Unauthorized",
      },
      {
        status: 401,
      }
    );
  } else {
    return NextResponse.json(
      {
        message: "Authorized",
      },
      {
        status: 200
      }
    )
  }
}