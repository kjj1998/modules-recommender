export async function fetchUserData(userId, accessToken) {
  const response = await fetch(`http://localhost:8081/api/v1/students/${userId}`, {
    headers: { 'Authorization': `Bearer ${accessToken}` }
  })

  const data = await response.json()
  const student = data.data

  return student
}

export async function fetchRecommendations(userId, accessToken) {
  const response = await fetch(`http://localhost:8081/api/v1/recommendation/${userId}`, {
    headers: {
      'Authorization': `Bearer ${accessToken}`
    }
  })

  const data = await response.json()
  const recommendations = data.data

  return recommendations
}