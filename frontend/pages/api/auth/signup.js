async function handler(req, res) {
  const registrationDetails = req.body

  const response = await fetch(`http://localhost:8081/api/v1/auth/register`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(registrationDetails)
  })

  const result = await response.json()

  if (result.http_code === 400) {
    res.status(400).json({
      code: 400, 
      status: `Username ${req.body.studentId} has already been taken!`
    })
  } else if (result.http_code === 200) {
    res.status(200).json({ 
      code: 200,
      status: `User ${req.body.studentId} created successfully!`
    })
  } else {
    res.status(500).json({
      code: 500,
      status: "Internal Server Error." 
    })
  }
}

export default handler;