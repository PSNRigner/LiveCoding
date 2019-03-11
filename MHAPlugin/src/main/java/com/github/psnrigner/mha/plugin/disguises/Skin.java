package com.github.psnrigner.mha.plugin.disguises;

public enum Skin
{
    DEKU(
            "eyJ0aW1lc3RhbXAiOjE1NTE4ODcwOTUxMzMsInByb2ZpbGVJZCI6ImMxZjQ1Nzk2ZDJmOTQ2MjI5NDc1MmFmZTU4MzI0ZGVlIiwicHJvZmlsZU5hbWUiOiJSaWduZXIiLCJzaWduYXR1cmVSZXF1aXJlZCI6dHJ1ZSwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzVkMmU5ZmQxZTQ4NWE1NzQ0MDMyMWZlODA5YWJhNzU3MDRkYTI1YWY4MDBlOWE4MjY1YzM3ODkxYTZjZmEyNTUiLCJtZXRhZGF0YSI6eyJtb2RlbCI6InNsaW0ifX19fQ==",
            "Sf9T3GAH+lBn3gq1zFteV+1H3+/MsCxTfEvI8AOL64rtzNBgkcTj5agfHIO9hBwZ80LBgmdl7XGz1guaKHFxknhOMjLAKQh5CDaHu2IAPU4w/kSc/dUdXx66iR06I8Z0uCv7PZrIbbqiJgqVlHZIxh3cB0StsYXz51leWkDRovqvi0cFgN7XlA4l2WSzROwlLsA491YKytwiCalAqnWu37H9haunrUSLgI5S3CwbULrEYdHCrNRfJeHpA9qBuETkNW3Pv6tVRf5sHZhwP/rX8GnMr2ZpjgewC54GgtCv8Jgs3tol+5sp2wqsM1U71wE4rNyDtEY7+3fTpURHrlFeHPJMmF2lWSn4E+FBDqkxh4h4hyHDxwzEQGBHzWTtnxU1bFIKa1A1XVmyu2RCec5sd2qZuCJykMJIfGJSle6cZwE/QEBO87gxKfdGyf95Uq6zyp2J1j8IBlV5HgopmwCkItq52BJm/Xw+QdWkDUbbgeIyMlktRUGTyRlFZAhtvfOw5jBlptwNL+w3cFBWq0WeSp1XSnMXwUOGcqehlvb7t1QeNIBoeoyE0SXdC9yJ/1RbheEaaDm5rz2a/NPniRHCvq1KoIkvgyUxB2KPq6SnhiatSaMI1KrMp5nxzdIix1syL6nNQFbu1//LfnmsdcwJTuU46CKpafmh27A7lFBg8Eg="
    ),
    OCHACO(
            "eyJ0aW1lc3RhbXAiOjE1NTE4ODY4MDU1MTUsInByb2ZpbGVJZCI6ImMxZjQ1Nzk2ZDJmOTQ2MjI5NDc1MmFmZTU4MzI0ZGVlIiwicHJvZmlsZU5hbWUiOiJSaWduZXIiLCJzaWduYXR1cmVSZXF1aXJlZCI6dHJ1ZSwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzJiMGY0OGFiNjc0ZTU3YTJmNzBlOWEyYWNjMDdmYjg5ZTBlNDM1OWZiOTNmMGY1OGY2ODUwZWIwNmFhMzI3OGQiLCJtZXRhZGF0YSI6eyJtb2RlbCI6InNsaW0ifX19fQ==",
            "Mm8AWqEgQ9SZnoXOPnKzMtygYDB3vWR87kTXKCao4qDNn4NvR7Jw7xMrzPtfxGYk0kPRLu2DAbmEaqAY9yOpSOhpzBgIPdE+pKCelCiiLLu1mgLOo/iRp1eCn/PrdQvnTScJJIW4bXmljGtu2dheV8Mx24tjk3Z0TF9cjqEzyi53Bib4wkg5/UNx5zCeZQmTiTx6+NVfsvJPxyRwH7QZLtVypDNdfNKEeGoLXlAO/21xpJ6tm+t/gd+uiSgxGSV8r4vO9zW2I63AIQT9NMTzAiswP2AfcGR+LWFHrSTlXin8+RzggGnyvb3KMiWW0g26zvL7152UpQXCLNE3Wuntf8JLppxOz3EwUVXdE01uzRb0FcJHL8PlINeRTEZon33Cl2kx5Ei13uJuESxEag7J6rGitnMMdqUdqxb3Cqn9qno3Kylpcr55U1O/NQifQ+Uw4N4fixD7NZas1m5pa+NCmnbH6B9UAAr8EHTU1sj66b2iI+XAam4ObQDAlVWoiUcCPXaqGMIFde4WahAMppfzOLaBZr4ke/mkaKD647Wcj8eM7xw2gN7OUjYjzbs0+3ibtDMvfUecL1/XspASdzs0iRd/fE/zUM8c/oet+pk0RxqRT4VmV2/FLd91p6FNoZdSRzTZKxu6Vizh4uLP6Cp1fcd8vf62zVMifTlt2DgGIDM="
    );

    private final String texture;
    private final String signature;

    Skin(String texture, String signature)
    {
        this.texture = texture;
        this.signature = signature;
    }

    String getTexture()
    {
        return this.texture;
    }

    String getSignature()
    {
        return this.signature;
    }
}
