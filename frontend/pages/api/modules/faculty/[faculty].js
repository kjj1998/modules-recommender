async function handler(req, res) {
  if (req.method !== 'GET') {
    return
  }

  const { faculty } = req.query
  

  const response = await fetch(`http://${process.env.NEXT_PUBLIC_HOST}:8081/api/v1/modules/faculty/${faculty}`)
  const result = await response.json()

  // console.log(result)

  res.status(200).json({ data: result.data })
}

export default handler