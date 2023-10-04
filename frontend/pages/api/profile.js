import { decode } from 'next-auth/jwt';

async function handler(req, res) {
  if (req.method !== 'PUT') {
    return
  }

  const updatedProfile = req.body
  const sessionToken = req.cookies['next-auth.session-token'];
  const decodedSessionToken = await decode({
    token: sessionToken,
    secret: process.env.NEXTAUTH_SECRET,
  });

  const accessToken = decodedSessionToken.accessToken

  const response = await fetch(`http://localhost:8081/api/v1/students/update`, {
    method: 'PUT',
    headers: { 
      'Authorization': `Bearer ${accessToken}`, 
      'Content-Type': 'application/json' 
    },
    body: JSON.stringify(updatedProfile)
  })

  const result = await response.json()

  res.status(result.http_code).json({ 
    status: result.http_code,
    message: result.message 
  })
}

export default handler