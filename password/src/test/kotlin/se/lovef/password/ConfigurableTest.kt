package se.lovef.password

import org.junit.Test
import se.lovef.assert.v1.shouldEqual
import se.lovef.assert.v1.shouldNotEqual

class ConfigurableTest {

    @Test fun `configurable equality`() {
        TestConfigurable() shouldEqual TestConfigurable()
        TestConfigurable() shouldNotEqual TestConfigurable2()
        TestConfigurable("config") shouldEqual TestConfigurable("config")
        TestConfigurable("config") shouldNotEqual TestConfigurable2("config")
    }
}

private class TestConfigurable(
    override val configuration: String = "test-hasher"
) : Configurable()

private class TestConfigurable2(
    override val configuration: String = "test-hasher2"
) : Configurable()
