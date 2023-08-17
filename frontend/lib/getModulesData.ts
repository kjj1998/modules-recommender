export async function getModules(skip: number, limit: number): Promise<Module[] | undefined> {
  
  const res = await fetch(`http://localhost:8081/api/v1/modules/${skip}/${limit}`)

  if (!res.ok) return undefined

  const resJson : ApiResponse = await res.json()
  const modules : Module[] = resJson.data
  return modules
}