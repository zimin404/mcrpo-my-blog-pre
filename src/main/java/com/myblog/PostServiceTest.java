@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Test
    void shouldCreatePost() {
        Post post = new Post();
        post.setTitle("Test");
        post.setText("Hello");

        Post created = postService.createPost(post);

        assertNotNull(created.getId());
    }
}
