package ru.ylab.application.service;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import ru.ylab.adapters.in.web.dto.RegisterDto;
import ru.ylab.application.out.AuditRepository;
import ru.ylab.application.out.UserRepository;

class RegisterUserImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuditRepository auditRepository;

    @InjectMocks
    private RegisterUserImpl registerUser;

    private RegisterDto validRegisterDto;

    private RegisterDto invalidRegisterDto;

//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        validRegisterModel = new RegisterModel("testUser", "password");
//        invalidRegisterModel = new RegisterModel("21", "password");
//    }
//
//    @Test
//    void testExecute_SuccessfulRegistration() {
//        when(userRepository.isAlreadyExists(any())).thenReturn(false);
//
//        registerUser.execute(validRegisterModel);
//
//        verify(userRepository, times(1)).save(any());
//        verify(auditRepository, times(1)).save(any());
//    }
//
//    @Test
//    void testExecute_UsernameAlreadyExists() {
//        when(userRepository.isAlreadyExists(any())).thenReturn(true);
//
//        assertThatThrownBy(() -> registerUser.execute(validRegisterModel))
//                .isInstanceOf(UsernameAlreadyExistsException.class);
//
//        verify(userRepository, never()).save(any());
//        verify(auditRepository, never()).save(any());
//    }
//
//    @Test
//    void testExecute_InvalidUsernameOrPassword() {
//        assertThatThrownBy(() -> registerUser.execute(invalidRegisterModel))
//                .isInstanceOf(NotValidUsernameOrPasswordException.class);
//
//        verify(userRepository, never()).save(any());
//        verify(auditRepository, never()).save(any());
//    }
}