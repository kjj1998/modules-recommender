export async function getModules(skip: number, limit: number): Promise<Module[] | undefined> {
  
  const res = await fetch(`http://localhost:8081/api/v1/modules/${skip}/${limit}`)

  if (!res.ok) return undefined

  const resJson : ApiResponseModules = await res.json()
  const modules : Module[] = resJson.data
  return modules
}

export async function getModuleByCourseCode(courseCode: string): Promise<Module | undefined> {
  console.log(courseCode)

  const res = await fetch(`http://localhost:8081/api/v1/modules/${courseCode}`)

  if (!res.ok) return undefined

  const resJson: ApiResponseModule = await res.json()
  const individualModule: Module = resJson.data

  return individualModule
}