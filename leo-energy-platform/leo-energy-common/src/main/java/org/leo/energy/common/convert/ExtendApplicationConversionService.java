package org.leo.energy.common.convert;

import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.lang.Nullable;
import org.springframework.util.StringValueResolver;

/**
 * @Author:gonglong
 * @Date:2022/4/13 9:09
 */
public class ExtendApplicationConversionService extends ApplicationConversionService {
    @Nullable
    private static volatile ExtendApplicationConversionService SHARED_INSTANCE;

    public ExtendApplicationConversionService() {
        this(null);
    }

    public ExtendApplicationConversionService(@Nullable StringValueResolver embeddedValueResolver) {
        super(embeddedValueResolver);
        super.addConverter(new EnumToStringConverter());
        super.addConverter(new StringToEnumConverter());
    }

    /**
     * Return a shared default application {@code ConversionService} instance, lazily
     * building it once needed.
     * <p>
     * Note: This method actually returns an {@link ExtendApplicationConversionService}
     * instance. However, the {@code ConversionService} signature has been preserved for
     * binary compatibility.
     * @return the shared {@code ExtendApplicationConversionService} instance (never{@code null})
     */
    public static GenericConversionService getInstance() {
        ExtendApplicationConversionService sharedInstance = ExtendApplicationConversionService.SHARED_INSTANCE;
        if (sharedInstance == null) {
            synchronized (ExtendApplicationConversionService.class) {
                sharedInstance = ExtendApplicationConversionService.SHARED_INSTANCE;
                if (sharedInstance == null) {
                    sharedInstance = new ExtendApplicationConversionService();
                    ExtendApplicationConversionService.SHARED_INSTANCE = sharedInstance;
                }
            }
        }
        return sharedInstance;
    }
}
