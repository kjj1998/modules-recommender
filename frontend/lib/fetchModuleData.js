export async function fetchAllFaculties() {
  const response = await fetch(`http://localhost:8081/api/v1/modules/faculties`)

  const data = await response.json()
  const faculties = data.data

  return faculties
}

export async function fetchAllModulesByFaculties(faculty) {
  const response = await fetch(`http://localhost:8081/api/v1/modules/faculty/${faculty}`)

  const data = await response.json()
  const modules = data.data

  return modules
}

export async function fetchModulesBySearchTerm(term, skip) {
  const response = await fetch(`http://localhost:8081/api/v1/modules/search/${term}/${skip}/10`)

  const data = await response.json()
  const modules = data.data

  return modules
}