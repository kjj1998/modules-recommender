export async function getCourses(skip: number, limit: number): Promise<Course[] | undefined> {
  
  const res = await fetch(`http://localhost:8081/api/v1/modules/${skip}/${limit}`)

  if (!res.ok) return undefined

  const resJson : ApiResponseCourses = await res.json()
  const courses : Course[] = resJson.data
  return courses
}

export async function getCourseByCourseCode(courseCode: string): Promise<Course | undefined> {
  
  const res = await fetch(`http://localhost:8081/api/v1/modules/${courseCode}`)

  if (!res.ok) return undefined

  const resJson: ApiResponseCourse = await res.json()
  const course: Course = resJson.data

  return course
}

export async function searchCourses(searchTerm: string): Promise<Course[] | undefined> {

  const response = await fetch(`http://localhost:8081/api/v1/modules/search/${searchTerm}/0/10`)
  const apiResponseCourses : ApiResponseCourses = await response.json()
  const courses : Course[] = apiResponseCourses.data

  return courses
}