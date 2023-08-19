import { NextResponse } from "next/server";

async function fetchCourseSearchResult(searchTerm: string | null) {
  const response = await fetch(`http://localhost:8081/api/v1/modules/search/${searchTerm}/0/10`, 
  {
    "method": "GET",
  })
      
  const apiResponseCourses : ApiResponseCourses = await response.json()

  const courseResults : Course[] = apiResponseCourses.data

  console.log(courseResults)

}

export async function GET(request: Request) {
  const { searchParams } = new URL(request.url);
  const query: string | null = searchParams.get('query');

  const courses = await fetchCourseSearchResult(query);
  
  return NextResponse.json(courses);
}